package br.com.gamemods.computercraft.integration.projectred.block;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import mrtjp.projectred.api.IBundledTile;
import mrtjp.projectred.api.ProjectRedAPI;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityProjectRedPeripheral extends TileEntity implements IPeripheral, IBundledTile {
	private static final Object[] sides = { "bottom", "top", "front", "back", "right", "left" };
	private byte[][] bundledSignal = new byte[6][16];
	private byte[] redstoneSignal = new byte[6];
	private int attachedComputers = 0;
	
	public TileEntityProjectRedPeripheral(){}
	
	private static int parseSide(String side) throws Exception
    {
		for (int n = 0; n < sides.length; n++)
		{
			if (side.equals(sides[n]))
			{
				return n;
			}
		}
		throw new Exception("Invalid side.");
	}
	
	@Override
	public String getType() {
		return "bundledio";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{
			"setBundledOutput", //0
			"getBundledOutput", //1
			"getBundledInput",  //2
			"setAnalogBundledOutput", // 3
			"setOutput",        //4
			"setAnalogOutput",  //5
			"getOutput",        //6
			"getAnalogOutput",  //7
			"getSides",			//8
			"getInput",			//9
		};
	}
	public void setAnalogBundledOutput(int side, int colors, byte strength)
	{
		for(int i = 0; i < 16; i++)
		{
			bundledSignal[side][i] = (((1 << i) & colors) != 0)? strength : 0;
		}
		
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, BlockProjectRedPeripheral.blockId);
	}
	
	public void setAnalogOutput(int side, int strength)
	{
		redstoneSignal[side] = (byte)strength;
		
		worldObj.notifyBlockChange(xCoord, yCoord, zCoord, BlockProjectRedPeripheral.blockId);
	}
	
	public static int getPoweredColors(byte[] signals)
	{
		int colors = 0;
		for(int i = 0; i < 16; i++)
		{
			if(signals[i] != 0)
				colors |= 1 << i;
		}
		return colors;
	}

	private int convertDirectionToForgeDirection(int direction)
	{
		switch(direction)
		{
		case 0: return 3;
		case 1: return 4;
		case 2: return 2;
		case 3: return 5;
		}
		return direction;
	}
	
	public int convertForgeDirectionToLocalFace(int forgeDirection)
	{
		int rawDirection = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		int direction = convertDirectionToForgeDirection(rawDirection);
		
		if(direction == ForgeDirection.NORTH.ordinal()) return forgeDirection;
		
		// Frente
		if(direction == forgeDirection) return ForgeDirection.NORTH.ordinal();
		
		ForgeDirection d = ForgeDirection.VALID_DIRECTIONS[direction]; 
		
		// Costas
		if(d.getOpposite().ordinal() == forgeDirection) return ForgeDirection.SOUTH.ordinal();
		
		// Cima
		if(d.getRotation(ForgeDirection.EAST).ordinal() == forgeDirection) return ForgeDirection.UP.ordinal();
		
		// Baixo
		if(d.getRotation(ForgeDirection.WEST).ordinal() == forgeDirection) return ForgeDirection.DOWN.ordinal();
		
		// Direita
		if(d.getRotation(ForgeDirection.UP).ordinal() == forgeDirection) return ForgeDirection.EAST.ordinal();
		
		// Esquerda
		if(d.getRotation(ForgeDirection.DOWN).ordinal() == forgeDirection) return ForgeDirection.WEST.ordinal();
		
		return 0;
	}
	
	public int getRedstonePower(int forgeDirection)
	{
		int convertedDirection =  convertForgeDirectionToLocalFace(forgeDirection) ;
		return redstoneSignal[convertedDirection];
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] args) throws Exception {
		try{
			//void setBundledOutput side colors
			//void setAnalogBundledOutput side colors strength
			if(method == 0 || method == 3){
				if((method == 0 && args.length != 2) || args[0] == null || args[1] == null || !(args[0] instanceof String) || !(args[1] instanceof Double))
					throw new Exception("Expected string, number");
	
				if(method == 3 && (args.length != 3 || !(args[2] instanceof Double)))
					throw new Exception("Expected string, number, number");
					
				int side = parseSide((String)args[0]);
				int colors = ((Double) args[1]).intValue();
				byte strength = (byte) (args.length == 3? ((Double) args[2]).intValue() & 255 : 255);
				
				setAnalogBundledOutput(side, colors, strength);
				return null;
			}
			
			//number getBundledOutput side
			else if(method == 1){
				if(args.length != 1 || args[0] == null || !(args[0] instanceof String))
					throw new Exception("Expected string");
				
				int side = parseSide((String)args[0]);
				
				return new Object[]{ getPoweredColors(bundledSignal[side]) };
			}
			
			//number getBundledInput side
			else if(method == 2){
				if(args.length != 1 || args[0] == null || !(args[0] instanceof String))
					throw new Exception("Expected string");
				
				if(ProjectRedAPI.transmissionAPI == null)
					return new Object[]{ 0 };
				
				int side = parseSide((String)args[0]);
				byte[] inputSignal = ProjectRedAPI.transmissionAPI.getBundledInput(worldObj, xCoord, yCoord, zCoord, side);
				
				return new Object[]{ getPoweredColors(inputSignal) };
			}
			
			//void setOutput side boolean
			else if(method == 4){
				if(args.length != 2 || args[0] == null || !(args[0] instanceof String) || args[1] == null || !(args[1] instanceof Boolean))
					throw new Exception("Expected string, boolean");
				
				setAnalogOutput(parseSide((String)args[0]), args[1] == Boolean.TRUE? 15 : 0);
				return null;
			}
			
			//void setAnalogOutput side strength
			else if(method == 5){
				if(args.length != 2 || args[0] == null || !(args[0] instanceof String) || args[1] == null || !(args[1] instanceof Double))
					throw new Exception("Expected string, number");
				
				setAnalogOutput(parseSide((String)args[0]), ((Double)args[1]).intValue());
				return null;
			}
			
			//boolean getOutput side
			else if(method == 6){
				if(args.length != 1 || args[0] == null || !(args[0] instanceof String))
					throw new Exception("Expected string");
				
				int side = parseSide((String)args[0]);
				return new Object[]{ getRedstonePower(side) != 0 };
			}
			
			//number getAnalogOutput side
			else if(method == 7){
				if(args.length != 1 || args[0] == null || !(args[0] instanceof String))
					throw new Exception("Expected string");
				
				int side = parseSide((String)args[0]);
				return new Object[]{ getRedstonePower(side) };
			}
			
			//strings getSides
			else if(method == 8){
				if(args.length != 0)
					throw new Exception("Unexpected args");
				return sides;
			}
			
			//boolean getInput side
			else if(method == 9)
			{
				if(args.length != 1 || args[0] == null || !(args[0] instanceof String))
					throw new Exception("Expected string");
				
				int side = parseSide((String)args[0]);
			}
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
			throw new Exception(e.toString()); 
		}
		throw new Exception("Unexpected method"); 
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		attachedComputers++;
	}

	@Override
	public void detach(IComputerAccess computer) {
		attachedComputers--;
		if(attachedComputers == 0)
		{
			bundledSignal = new byte[6][16];
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, BlockProjectRedPeripheral.blockId);
		}
	}

	@Override
	public byte[] getBundledSignal(int side) {
		return bundledSignal[ convertForgeDirectionToLocalFace( side ) ];
	}

	@Override
	public boolean canConnectBundled(int side) {
		return true;
	}

}

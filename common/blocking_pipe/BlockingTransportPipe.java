package blocking_pipe;

import net.minecraft.tileentity.TileEntity;

public class BlockingTransportPipe extends TileEntity {
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
	}
}

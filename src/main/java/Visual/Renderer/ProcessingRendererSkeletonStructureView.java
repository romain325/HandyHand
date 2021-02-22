package Visual.Renderer;

import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import com.leapmotion.leap.Controller;

import javax.management.BadAttributeValueExpException;

public class ProcessingRendererSkeletonStructureView extends ProcessingRenderer {
    private SkeletonStructureView skeletonStructureView;

    public ProcessingRendererSkeletonStructureView(Controller controller, SkeletonStructureView skeletonStructureView) throws BadAttributeValueExpException {
        super(controller, skeletonStructureView);
        if(skeletonStructureView == null) throw new BadAttributeValueExpException("SkeletonStructureView has to be not null");

        setSkeletonStructureView(skeletonStructureView);
    }

    public SkeletonStructureView getSkeletonStructureView() {
        return skeletonStructureView;
    }

    private void setSkeletonStructureView(SkeletonStructureView skeletonStructureView) {
        this.skeletonStructureView = skeletonStructureView;
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        skeletonStructureView.resetDefineStructure();
    }
}

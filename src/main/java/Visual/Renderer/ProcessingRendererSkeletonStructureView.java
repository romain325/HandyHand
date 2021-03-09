package Visual.Renderer;

import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import com.leapmotion.leap.Controller;

import javax.management.BadAttributeValueExpException;

/**
 * A class to implements more possibility for the SkeletonStructureView
 */
public class ProcessingRendererSkeletonStructureView extends ProcessingRenderer {
    /**
     * The SkeletonStructureView that are currently used
     */
    private SkeletonStructureView skeletonStructureView;

    /**
     * A constructor of the class ProcessingRendererSkeletonStructureView
     * @param controller The controller from which we will receive information of
     * @param skeletonStructureView The SkeletonStructureView that are currently used
     * @throws BadAttributeValueExpException If the SkeletonStructureView are null
     */
    public ProcessingRendererSkeletonStructureView(Controller controller, SkeletonStructureView skeletonStructureView) throws BadAttributeValueExpException {
        super(controller, skeletonStructureView);
        if(skeletonStructureView == null) throw new BadAttributeValueExpException("SkeletonStructureView has to be not null");

        setSkeletonStructureView(skeletonStructureView);
    }

    /**
     * The getter of the SkeletonStructureView that are currently used
     * @return The SkeletonStructureView that are currently used
     */
    public SkeletonStructureView getSkeletonStructureView() {
        return skeletonStructureView;
    }

    /**
     * The setter of the SkeletonStructureView that are currently used
     * @param skeletonStructureView The SkeletonStructureView that are currently used
     */
    private void setSkeletonStructureView(SkeletonStructureView skeletonStructureView) {
        this.skeletonStructureView = skeletonStructureView;
    }

    /**
     * The method that are called when a key is pressed
     * This one will reset the defined Structure in the SkeletonStructureView that are currently used
     */
    @Override
    public void keyPressed() {
        super.keyPressed();
        skeletonStructureView.resetDefineStructure();
    }
}

package Core.StubPersistence;

import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Script.Script;
import Core.StubPersistence.Local.GestureManager;

/**
 * Interface for the gesture's data
 */
public interface IGestureDataManager extends ILoadDataManager<GestureStructure>,ISaveDataManager<GestureStructure> {
}

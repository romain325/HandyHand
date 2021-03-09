package Core.StubPersistence;

import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Script.Script;
import Core.StubPersistence.Local.GestureManager;

public interface IGestureDataManager extends ILoadDataManager<GestureStructure>,ISaveDataManager<GestureStructure> {
}

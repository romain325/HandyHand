export interface ScriptCard {
  file: string;
  description: string;
  id: string;
  idGesture: string;
  status: string;
}

export interface NewScript {
  file: string;
  description: string;
  args: string[];
  execType: string;
}

export interface UserCreds {
  mail: string;
  password: string;
}

export interface GestureCard {
  name: string;
  description: string;
  isDistanceImportant: boolean;
  isDoubleHand: boolean;
  id: string;
}

export interface ExecInfo {
  id: string;
  name: string;
  execPath: string;
}

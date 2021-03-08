export interface ScriptCard {
  file: string;
  description: string;
  id: string;
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
  _id: string;
}

export interface ExecInfo {
  id: string;
  name: string;
  execPath: string;
}

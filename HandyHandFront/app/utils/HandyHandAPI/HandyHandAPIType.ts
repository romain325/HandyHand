/**
 * Get a script card
 */
export interface ScriptCard {
  file: string;
  description: string;
  id: string;
  idGesture: string;
  status: string;
}

/**
 * Send a new script
 */
export interface NewScript {
  file: string;
  description: string;
  args: string[];
  execType: string;
}

/**
 * User credentials
 */
export interface UserCreds {
  mail: string;
  password: string;
}

/**
 * Gesture informations
 */
export interface GestureCard {
  name: string;
  description: string;
  isDistanceImportant: boolean;
  isDoubleHand: boolean;
  id: string;
}

/**
 * Inforamtions about an executable
 */
export interface ExecInfo {
  id: string;
  name: string;
  execPath: string;
}

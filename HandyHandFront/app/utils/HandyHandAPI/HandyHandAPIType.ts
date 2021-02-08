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

export interface NewUser {
  mail: string;
  password: string;
}

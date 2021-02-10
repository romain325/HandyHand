import {NewScript, ScriptCard, UserCreds} from './HandyHandAPIType';

export default class HandyHandAPI {
  private link = 'http://localhost:8080';

  private async getFromAPI<T>(urlArg: string): Promise<T> {
    const rep = await fetch(this.link + urlArg);
    return await rep.json();
  }

  private async postToAPI<T>(urlArg: string, data : T) : Promise<Response> {
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    };
    return await fetch(this.link + urlArg, options);
  }

  public async getScriptCards(): Promise<ScriptCard[]> {
    return await this.getFromAPI<ScriptCard[]>('/script/all');
  }

  public async addNewScript(elem: NewScript): Promise<string> {
    return (await this.postToAPI('/script/add', elem)).text();
  }

  public async createNewUser(elem: UserCreds): Promise<string> {
    return( await this.postToAPI('/user/add', elem)).json();
  }

  public async connectUser(elem: UserCreds): Promise<string> {
    return (await this.postToAPI('/user/connect', elem)).text();
  }
}

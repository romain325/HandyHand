import {ExecInfo, GestureCard, NewScript, ScriptCard, UserCreds} from './HandyHandAPIType';
import { getAuthedHeader } from '../../features/connection/Connexion';

export default class HandyHandAPI {
  private link = 'http://localhost:8080';

  private async getFromAPI<T>(urlArg: string): Promise<T> {
    const rep = await fetch(this.link + urlArg);
    return await rep.json();
  }

  private async postToAPI<T>(urlArg: string, data: T): Promise<Response> {
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    };
    return await fetch(this.link + urlArg, options);
  }

  private async actionToAPI<T>(
    urlArg: string,
    method: string,
    authed = false,
    data: T = ''
  ): Promise<Response> {
    const options = {
      method,
      headers: {},
      body: data.toString(),
    };
    if (data != '') {
      options.body = JSON.stringify(data);
    }
    if (authed) {
      options.headers = getAuthedHeader();
    }

    return await fetch(this.link + urlArg, options);
  }

  public async getScriptCards(): Promise<ScriptCard[]> {
    return await this.getFromAPI<ScriptCard[]>('/script/all');
  }

  public async addNewScript(elem: NewScript): Promise<string> {
    return (await this.postToAPI('/script/add', elem)).text();
  }

  public async addNewGesture(elem: GestureCard): Promise<Response> {
    return (await this.postToAPI('/gesture/add', elem));
  }

  public async modifyExec(elem: ExecInfo): Promise<string> {
    return (await this.postToAPI('/exec/modify', elem)).text();
  }

  public async removeScript(id: string) {
    await this.actionToAPI(`/script/${id}`, 'DELETE').finally(async () => {
      await this.actionToAPI(`/scriptDB/${id}`, 'DELETE', true);
    });
  }

  public async removeGesture(id: string) {
    await this.actionToAPI(`/gesture/${id}`, 'DELETE');
  }

  public async removeGestureDb(id: string) {
    return await this.actionToAPI(`/gestureDB/${id}`, 'DELETE');
  }

  public async createNewUser(elem: UserCreds): Promise<string> {
    return (await this.postToAPI('/user/add', elem)).json();
  }

  public async connectUser(elem: UserCreds): Promise<string> {
    return (await this.postToAPI('/user/connect', elem)).text();
  }
}

import {
  ExecInfo,
  GestureCard,
  NewScript,
  ScriptCard,
  UserCreds,
} from './HandyHandAPIType';
import { getAuthedHeader } from '../../features/connection/Connexion';
import { getAddress } from './HandyHandConfig';

export default class HandyHandAPI {
  private link = getAddress();

  private async getFromAPI<T>(urlArg: string): Promise<T> {
    const rep = await fetch(this.link + urlArg);
    return await rep.json();
  }

  private async postToAPI<T>(urlArg: string, data: T, isAuthed : boolean): Promise<Response> {
    const options = {
      method: 'POST',
      headers: isAuthed
        ? getAuthedHeader()
        : { 'Content-Type': 'application/json' },
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
    return (await this.postToAPI('/script/add', elem, false)).text();
  }

  public async addNewGesture(elem: GestureCard): Promise<Response> {
    return (await this.postToAPI('/gesture/add', elem, false));
  }

  public async modifyExec(elem: ExecInfo): Promise<string> {
    return (await this.postToAPI('/exec/modify', elem, false)).text();
  }

  public async removeScript(id: string, isOnline : boolean) : Promise{
    const action = isOnline ? 'scriptDB' : 'script';
    return await this.actionToAPI(`/${action}/${id}`, 'DELETE', false);
  }

  public async modifyScript(script: any, isOnline: boolean) : Promise {
    const action = isOnline ? 'scriptDB' : 'script';
    return await this.postToAPI(`/${action}/modify`, script, isOnline);
  }

  public async removeGesture(id: string) {
    await this.actionToAPI(`/gesture/${id}`, 'DELETE');
  }

  public async removeGestureDb(id: string) {
    return await this.actionToAPI(`/gestureDB/${id}`, 'DELETE');
  }

  public async createNewUser(elem: UserCreds): Promise<string> {
    return (await this.postToAPI('/user/add', elem, false)).json();
  }

  public async connectUser(elem: UserCreds): Promise<string> {
    return (await this.postToAPI('/user/connect', elem, false)).text();
  }

  public async isAlive(): Promise<boolean> {
    return await this.getFromAPI('/api/check');
  }

  public async isConnected(): Promise<boolean> {
    return await this.getFromAPI('/leap/state');
  }

  public async switchScript(id: string, isActive: boolean, isOnline: boolean) : Promise<Response> {
    const place = isOnline ? 'scriptDB' : 'script';
    const action = isActive ? 'stop' : 'launch';
    return await this.postToAPI(`/${place}/${action}`, { scriptId: id }, isOnline);
  }
}

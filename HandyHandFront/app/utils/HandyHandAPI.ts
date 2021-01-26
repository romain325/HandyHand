import { ScriptCard } from './HandyHandAPIType';

export default class HandyHandAPI {
  private link = 'http://localhost:8080';

  private async getFromAPI<T>(urlArg: string): Promise<T> {
    const rep = await fetch(this.link + urlArg);
    return await rep.json();
  }

  public async getScriptCards(): Promise<ScriptCard[]> {
    return await this.getFromAPI<ScriptCard[]>('/script/all');
  }
}

import { ScriptCard } from './HandyHandAPIType';

const link = 'http://localhost:8080/';

async function getFromAPI<T>(urlArg: string): Promise<T> {
  const rep = await fetch(link + urlArg);
  return await rep.json();
}

export default async function getScriptCards(): Promise<ScriptCard[]> {
  return await getFromAPI<ScriptCard[]>('/script/all');
}

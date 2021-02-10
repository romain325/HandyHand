import fs from 'fs';

const tokenFile = 'user.token';

export function saveToken(token: string): void{
  fs.writeFileSync(tokenFile, token, { encoding: 'utf8' });
}

export function readToken(): string {
  return fs.readFileSync(tokenFile, 'utf8');
}

export function hasToken(): boolean {
  return fs.existsSync(tokenFile);
}

import fs from 'fs';

const tokenFile = 'user.token';

export function saveToken(token: string): void {
  fs.writeFileSync(tokenFile, token, { encoding: 'utf8' });
}

export function readToken(): string {
  return fs.readFileSync(tokenFile, 'utf8');
}

export function removeToken() {
  fs.unlinkSync(tokenFile);
}

export function hasToken(): boolean {
  return fs.existsSync(tokenFile);
}

export function getAuthedHeader(): Headers {
  const header = new Headers();
  header.set('Authorization', readToken());
  return header;
}

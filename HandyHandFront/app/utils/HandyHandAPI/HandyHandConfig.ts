import fs from 'fs';

const address = 'api.address';

export function getAddress(): string {
  if (!fs.existsSync(address)) {
    fs.writeFileSync(address, 'http://localhost:8080', { encoding: 'utf8' });
  }
  return fs.readFileSync(address, 'utf8');
}

export function setAddress(newAddress: string): void {
  fs.writeFileSync(address, newAddress, { encoding: 'utf8' });
}

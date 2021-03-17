import fs from 'fs';

const address = 'api.address';

/**
 * Get address from which you should call the API
 */
export function getAddress(): string {
  if(!fs.existsSync(address)){
    fs.writeFileSync(address, 'http://localhost:8080', { encoding: 'utf8' });
  }
  return fs.readFileSync(address, 'utf8');
}

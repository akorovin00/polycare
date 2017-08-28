import { Base } from './base';
import { Medication } from './medication';
export class CareplanTask extends Base {
  public title: string;
  public start: string;
  public end: string;
  public posology: string;
  public medication: Medication[];
}

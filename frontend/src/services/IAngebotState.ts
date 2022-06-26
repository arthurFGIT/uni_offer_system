import type { IAngebotListeItem } from "./IAngebotListeItem";

export interface IAngebotState {
    angebotliste: IAngebotListeItem[],
    errormessage: string
}
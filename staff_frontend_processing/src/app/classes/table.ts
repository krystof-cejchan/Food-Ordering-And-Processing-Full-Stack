import { Restaurant } from "./restaurant";

export interface Table {
    id: number;
    row: string;
    column: number;
    restaurantLocation: Restaurant;
}
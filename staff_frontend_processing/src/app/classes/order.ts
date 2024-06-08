import { Customer } from "./customer";
import { Food } from "./food";
import { Staff } from "./staff";
import { Table } from "./table";

export interface Order {
    order_id: string;
    items: Food[];
    table: Table;
    total: number;
    orderStatus: string;
    orderCreated: Date;
    customer: Customer;
    assignedStaff: Staff;
}

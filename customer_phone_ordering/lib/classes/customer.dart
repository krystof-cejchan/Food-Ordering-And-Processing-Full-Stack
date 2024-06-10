class Customer {
  int customer_id;
  String? name;
  String? lastName;
  Customer(this.customer_id, this.name, this.lastName);
  Customer.id(this.customer_id);

  Map<String, dynamic> toJson() => {'customer_id': customer_id};
}

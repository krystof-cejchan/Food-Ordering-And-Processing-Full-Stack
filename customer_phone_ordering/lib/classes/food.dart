class Food {
  int id;
  double price;
  String title;
  Food(this.id, this.price, this.title);

  factory Food.fromJson(Map<String, dynamic> json) {
    return switch (json) {
      {
        'id': int id,
        'title': String title,
        'price': double price,
      } =>
        Food(id, price, title),
      _ => throw const FormatException('Failed to load Food from json.'),
    };
  }
}

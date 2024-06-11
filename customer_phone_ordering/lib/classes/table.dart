class Table {
  int id;
  int restaurantId;
  String row;
  int column;
  Table(this.id, this.restaurantId, this.row, this.column);

  Map<String, dynamic> toJson() =>
      {'id': id, 'restaurantId': restaurantId, 'row': row, 'column': column};

  @override
  String toString() {
    return [id, row, column].join(' — ');
  }
}

mixin CurrentTable {
  static Table? table;
}

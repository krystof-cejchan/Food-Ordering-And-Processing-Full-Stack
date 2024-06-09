import 'package:customer_phone_ordering/classes/food.dart';

/// singleton design pattern
class BasketItemHolder {
  // Private constructor
  BasketItemHolder._privateConstructor();

  // Singleton instance
  static final BasketItemHolder _instance =
      BasketItemHolder._privateConstructor();

  // Factory constructor to return the same instance every time
  factory BasketItemHolder() {
    return _instance;
  }

  final List<Food> _basketContent = List.empty(growable: true);

  void add(Food f) {
    _basketContent.add(f);
  }

  void remove(Food f) {
    _basketContent.remove(f);
  }

  Food get(int index) => _basketContent[index];

  int length() => _basketContent.length;

  List<Food> get basketContent => List.unmodifiable(_basketContent);
}

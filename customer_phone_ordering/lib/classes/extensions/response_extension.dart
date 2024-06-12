import 'package:http/http.dart' as http;

extension IsOk on http.Response {
  bool get ok => (statusCode ~/ 100) == 2;
}

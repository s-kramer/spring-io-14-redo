@RestController
class Greeting {
  @RequestMapping("/greet")
  String hello() {
    return 'hello'
  }
}

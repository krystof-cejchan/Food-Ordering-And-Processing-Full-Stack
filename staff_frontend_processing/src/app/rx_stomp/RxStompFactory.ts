import { myRxStompConfig } from "./RxStompConfig";
import { RxStompService } from "./RxStompService";


export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();
  rxStomp.configure(myRxStompConfig);
  rxStomp.activate();
  return rxStomp;
}

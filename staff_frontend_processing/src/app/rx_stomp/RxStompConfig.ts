import { RxStompConfig } from "@stomp/rx-stomp";

export const myRxStompConfig: RxStompConfig = {
  brokerURL: 'ws://localhost:8080/ws',
  logRawCommunication: true,
  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 500,
  // debug: (msg: string): void => console.log(new Date(), msg)
};
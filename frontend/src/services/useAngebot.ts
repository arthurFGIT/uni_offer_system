import { reactive, readonly } from "vue";
import type { IAngebotListeItem } from "./IAngebotListeItem";
import type { IAngebotState } from "./IAngebotState";
import type { IBackendInfoMessage } from "./IBackendInfoMessage";
import { Client, type Message } from '@stomp/stompjs';

const wsurl = `ws://${window.location.host}/stompbroker`;
const DEST = "/topic/angebot";


const angebotState = reactive(<IAngebotState>
    {
        angebotliste: [],
        errormessage: ""
    }
)

export function updateAngebote(){
    fetch('/api/angebot')
    .then((response) =>{
        if (!response.ok){
            throw new Error(response.statusText)
        }
        return response.json()
    })
    .then((jsonData) => {
        angebotState.angebotliste = jsonData
        console.log(JSON.stringify(jsonData))
    })
    .catch((error) =>{
        angebotState.errormessage = error
    })
}

export function useAngebot(){
    return {
        angebote: readonly(angebotState),
        updateAngebote,
        receiveAngebotMessages
    }
}

export function receiveAngebotMessages(){

    const stompclient = new Client({ brokerURL: wsurl })
    stompclient.onWebSocketError = (event) => { /* WS-Fehler */ }
    stompclient.onStompError = (frame) => { /* STOMP-Fehler */ }
    stompclient.onConnect = (frame) => {
        stompclient.subscribe(DEST, (message) => {
            updateAngebote();
            let backendMsg: IBackendInfoMessage = JSON.parse(message.body);
        });
    };
    stompclient.onDisconnect = () => { /* Verbindung abgebaut*/ }
    stompclient.activate();
        try {
            stompclient.publish({ destination: DEST, headers: {},
            body: JSON.stringify("")
        });
    } catch (fehler) {
    }
    
}

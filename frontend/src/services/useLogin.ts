import type { IAngebotListeItem } from "@/services/IAngebotListeItem";
import type { IBackendInfoMessage } from "./IBackendInfoMessage";
import { reactive, readonly } from "vue";
import router from '../router';

import { Client } from '@stomp/stompjs';
import { stringifyQuery } from "vue-router";
const wsurl = `ws://${window.location.host}/stompbroker`

interface ILoginState {
    username: string,
    name: string,
    benutzerprofilid: number,
    loggedin: boolean,
    jwtToken: string
    errormessage: string
}

const loginState: ILoginState = reactive<ILoginState>({
    username: "",
    name: "",
    benutzerprofilid: 0,
    loggedin: false,
    jwtToken: "",
    errormessage: ""
})

// Analog Java-Records auf Serverseite:
// public record JwtLoginResponseDTO(String username, String name, Long benutzerprofilid, String jwtToken) {};
interface IJwtLoginResponseDTO {
    username: string,
    name: string,
    benutzerprofilid: number,
    jwtToken: string
}

// public record JwtLoginRequestDTO(String username, String password) {};
interface IJwtLoginRequestDTO {
    username: string,
    password: string
}


async function login(username: string, password: string) {
   /*
    * sendet per fetch() POST auf Endpunkt /api/login ein IAddGebotRequestDTO als JSON
    * erwartet IJwtLoginResponseDTD-Struktur zurück (JSON)
    * 
    * Falls ok, wird 'errormessage' im State auf leer gesetzt,
    * die loginState-Eigenschaften aus der Antwort befüllt
    * und 'loggedin' auf true gesetzt
    * 
    * Falls Fehler, wird ein logout() ausgeführt und auf die Fehlermeldung in 'errormessage' geschrieben
    */

   fetch('/api/login',{
    method: 'POST',
        headers: {
        'Content-Type': 'application/json',
        },
        body: JSON.stringify(<IJwtLoginRequestDTO>({
            username,
            password
        })),
    })
   .then((response) =>{
       if (!response.ok){
           throw new Error(response.statusText)
       }
       return response.json()
   })
   .then((jsonData) => {
        const data = jsonData as IJwtLoginResponseDTO
        loginState.username = data.username
        loginState.benutzerprofilid = data.benutzerprofilid
        loginState.name = data.name
        loginState.jwtToken = data.jwtToken
        loginState.loggedin = true
        loginState.errormessage = ""
        router.push("/")
   })
   .catch((error) =>{
        logout()
        loginState.errormessage = error
   })   


}

function logout() {
    console.log(`logout(${loginState.name} [${loginState.username}])`)
    loginState.loggedin = false
    loginState.jwtToken = ""
    loginState.benutzerprofilid = 0
    loginState.name = ""
    loginState.username = ""
}


export function useLogin() {
    return {
        logindata: readonly(loginState),
        login,
        logout,
    }
}


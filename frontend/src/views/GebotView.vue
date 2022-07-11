<template>
    <div>
        <h4>Angebot: {{angebot.beschreibung}}</h4>
        <h6>Mindestgebot: {{angebot.mindestpreis}}</h6>
        <p>von {{angebot.anbietername}}, abholbar in <GeoLink :lat="angebot.lat" :long="angebot.lon">{{angebot.abholort}}</GeoLink>
        bis {{angebot.ablaufzeitpunkt}}</p>
    </div>
    <div v-if="angebote.errormessage != ''">
        {{angebote.errormessage}}
    </div>
    <div>
        <p>Das Topgebot ist von {{gebote.topbieter}} {{gebote.topgebot}}</p>
        <input type="number" min="1" v-model="betrag">
        <button :onclick="sendeGebot(betrag.valueOf())">Gebot senden</button>
    </div>
    <div>
        <table>
            <thead></thead>
            <tbody>
                <tr v-for="ele in geboteSorted">
                    <td>{{ele.gebotzeitpunkt}}</td>
                    <td>{{ele.gebietername}}</td>
                    <td>{{ele.betrag}} EUR</td>
                </tr>
            </tbody>
        </table>
    </div>


</template>

<script setup lang="ts">
import AngebotListe from '../components/AngebotListe.vue';
import { useAngebot } from '@/services/useAngebot';
import { useGebot } from '@/services/useGebot';
import GeoLink from '@/components/GeoLink.vue'
import { computed, ref } from '@vue/reactivity';
import { onMounted } from '@vue/runtime-dom';

let betrag = ref<Number>(0)

const props = defineProps<{
    angebotidstr: string
}>();

const {angebote} = useAngebot()

const angebotList = angebote.angebotliste.filter((angebot) =>{
    return angebot.angebotid == parseInt(props.angebotidstr)
})
const angebot = angebotList[0]
console.log(angebotList[0])

const {gebote, updateGebote, sendeGebot} = useGebot(parseInt(props.angebotidstr))

let geboteSorted = computed(()=>{
    return gebote.gebotliste.slice().sort((el1, el2) => {
        if (el1.gebotzeitpunkt > el2.gebotzeitpunkt){
            return 1
        } else if(el1.gebotzeitpunkt < el2.gebotzeitpunkt){
            return -1
        } else {
            return 0
        }
    }).slice(0,9)
})

onMounted( async () => {
  await updateGebote()
})

</script>
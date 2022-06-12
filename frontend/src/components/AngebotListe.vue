<template>
    <div class="flex-end input">
        <input type="text" v-model="suchfeld" placeholder = "Eingabe"/>
        <button class="btn" @click="clear()">clear</button>
    </div>
    <div>
        <AngebotListeItem :item="ele" v-for="ele in filteredAngebote" :key="ele.angebotid"></AngebotListeItem>
    </div>

</template>

<script setup lang="ts">
import AngebotListeItem from '@/components/AngebotListeItem.vue';
import { useFakeAngebot } from '@/services/useFakeAngebot';
import { ref, computed } from 'vue';

const {angebote, bietenSimulieren} = useFakeAngebot()
const suchfeld = ref("")

const filteredAngebote = computed(() => {
    return angebote.value.filter(e =>
    e.beschreibung.toLowerCase()
    .includes(suchfeld.value.toLowerCase()) ||
    e.abholort.toLowerCase()
    .includes(suchfeld.value.toLowerCase()) ||
    e.anbietername.toLowerCase()
    .includes(suchfeld.value.toLowerCase())
);

})

function clear(){
    suchfeld.value = ""
}
</script>

<style>


.flex{
    display: flex;
}
.flex-end{
    display: flex;
    justify-content: end;
}
.btn{
    color: white;
    background-color: #3D426B;
    border-radius: 3px;
    padding: 8px 30px;
    outline: none !important;
    border: none;
    cursor: pointer;
    outline: inherit;
    font-size: 14px;
}


.input input{
    padding: 5px;
    color: #343a40;
    background-color: #3D426B10;
    height: 30px;
    border: none;
    border-radius: 3px;
    display: flex;
}
</style>
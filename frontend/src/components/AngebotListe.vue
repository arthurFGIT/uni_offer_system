<template>
    <div class="flex-end input">
        <input type="text" v-model="suchfeld" placeholder = "Eingabe"/>
        <button class="btn" @click="clear()">clear</button>
        <button @click="updateAngebote()">Reload</button>

    </div>
    <div>
        <AngebotListeItem :item="ele" v-for="ele in filteredAngebote" :key="ele.angebotid"></AngebotListeItem>
    </div>

</template>

<script setup lang="ts">
import AngebotListeItem from '@/components/AngebotListeItem.vue';
import { useAngebot } from '@/services/useAngebot';
import { ref, computed } from 'vue';

const {angebote, updateAngebote} = useAngebot()
const suchfeld = ref("")

const filteredAngebote = computed(() => {
    return angebote.angebotliste.filter(e =>
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
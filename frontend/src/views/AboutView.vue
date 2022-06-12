<template>

  <GeoLink :lat="12" :long="9.34">GeoLink Test</GeoLink>

  <div class="box">
    <h2>Angebot Test</h2>
    <AngebotListeItem :item="a"></AngebotListeItem>
    <div class="flex-end">
        <button class="btn" @click="bietenSimulieren()">bieten Simulieren</button>
    </div>
    
    <h2>Angebot Liste</h2>
    <AngebotListe></AngebotListe>
  
  </div>
</template>

<style>
body{
    background-color: #f3f3f9;
}
p{
    margin: 0 !important;
}

.box{
    border-radius: 10px;
    box-shadow: 0px 0px 15px -2px rgba(0,0,0,0.31);
    background-color: white;
    margin: 10% auto;
    width: 1000px;
    height: 680px;
    padding: 50px;
}

.flex{
    display: flex;
}
.flex-end{
    display: flex;
    justify-content: end;
}

.text{
    display: flex;
    border-bottom: 2px solid #3D426B20;
    justify-content: space-between;
}

h1, h2, h3, h4, p, label, strong, a, li{
    font-family: 'Poppins', sans-serif;
    color: #343a40;
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
</style>
<script setup lang="ts">
import GeoLink from '@/components/GeoLink.vue'
import AngebotListeItem from '@/components/AngebotListeItem.vue'
import { useFakeAngebot } from '@/services/useFakeAngebot';
import AngebotListe from '../components/AngebotListe.vue';

const {angebote, bietenSimulieren} = useFakeAngebot()

const a = angebote.value[0]


</script>
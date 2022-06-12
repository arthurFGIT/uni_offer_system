<template>

    <table class="table">
        <thead>
            <tr>
                <th>{{item.beschreibung}}</th>
                <th>{{item.gebote}}</th>
                <th>{{item.topgebot}}</th>
                <th style="text-align: right"><button class="btn" @click="open()">V</button></th>
            </tr>
        </thead>

        <tbody v-if="isOpen">
            <tr>
                <td>Letztes Gebot</td>
                <td>{{item.topgebot}}</td>
            </tr>
            <tr>
                <td>Abholort</td>
                <td><GeoLink :lat="item.lat" :long="item.lon">{{item.abholort}}</GeoLink></td>
            </tr>
            <tr>
                <td>bei</td>
                <td>{{item.anbietername}}</td>
            </tr>
            <tr>
                <td>bis</td>
                <td>{{item.ablaufzeitpunkt}}</td>
            </tr>
        </tbody>
    </table>
</template>

<script setup lang="ts">
import GeoLink from '@/components/GeoLink.vue'
import type { IAngebotListeItem } from '@/services/IAngebotListeItem';
import {defineProps, ref } from 'vue';

const isOpen = ref(false)

const props = defineProps<{
    item: IAngebotListeItem
}>()

function open(): void{
    isOpen.value = !isOpen.value
}

</script>

<style>
.table{
    width: 100%;
    text-align: left;
    border-radius: 10px;
    box-shadow: 0px 0px 15px -2px rgba(0,0,0,0.15);
    background-color: white;
    padding: 10px;
    margin: 10px;
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
<template>
    <a target="_blank" :href="link"><slot></slot></a>
</template>



<script setup lang="ts">
import {defineProps, withDefaults } from 'vue';


const props = withDefaults(defineProps<{
    lat: number,
    long: number,
    zoom: number
}>(), {zoom: 17});

const link = `https://www.openstreetmap.org/#map=${props.zoom}/${props.lat}/${props.long}`


</script>
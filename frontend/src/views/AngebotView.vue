<template>
    <h2>Wir haben aktuell {{angebote.angebotliste.length}} Angebote für Sie</h2>
    <AngebotListe></AngebotListe>
    <div v-if="angebote.errormessage != ''">
        {{angebote.errormessage}}
    </div>
    <div v-if="angebote.errormessage != ''">
        {{angebote.errormessage}}
    </div>
</template>

<script setup lang="ts">
import AngebotListe from '../components/AngebotListe.vue';
import { useAngebot } from '@/services/useAngebot';
import { onMounted } from 'vue';

const {angebote, updateAngebote, receiveAngebotMessages} = useAngebot()

onMounted(async () => {
    updateAngebote(),
    receiveAngebotMessages()
});

</script>
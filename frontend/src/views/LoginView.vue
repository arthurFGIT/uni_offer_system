<template>
    <input v-model="username">
    <input v-model="password">
    <button v-on:click="useLogin().login(username, password)">Einloggen</button>
    <button v-on:click="useLogin().logout()">Ausloggen</button>
</template>  

<script setup lang="ts">
import router from '@/router';
import { useLogin } from '@/services/useLogin';
import { onMounted, ref } from 'vue';

let username = ref("")
let password = ref("")

const {logindata, login, logout} = useLogin()

onMounted(async () => {
    useLogin().logout()
});

</script>
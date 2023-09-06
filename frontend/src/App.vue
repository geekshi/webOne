<template>
    <div>
        <StockPull></StockPull>
        <hr />
        <StockPush></StockPush>
    </div>
</template>

<script>
    import StockPull from './components/StockPull.vue'
    import StockPush from './components/StockPush.vue'

    export default {
        name:'App',
        components:{
            StockPull,
            StockPush
        }
    }
</script>
<template>
    <div>
        <StockInfo></StockInfo>
        <hr />
        <StockPrice></StockPrice>
    </div>
</template>

<script>
    import StockInfo from './components/StockInfo.vue'
    import StockPrice from './components/StockPrice.vue'

    export default {
        name:'App',
        components:{
            StockInfo,
            StockPrice
        }
    }
</script>
<template>
    <div>
        <h2>PULL MODE</h2>
        <p>Name: {{name}}</p>
        <p>Code: {{code}}</p>
        <p>Price: {{price}}</p>
        <button @click="refreshPrice">Refresh price</button>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name:'StockPull',
        data() {
            return {
                name: '',
                code: '',
                price: ''
            }
        },
        methods:{
            refreshPrice() {
                const url = `http://localhost:8081/api/stocks/004`
                axios.get(url).then(res => {
                    const result = res.data
                    this.price = result.price
                }).catch(error => {
                    console.log(error)
                })
            }
        },
        mounted() {
            const url = `http://localhost:8081/api/stocks/004`
            axios.get(url).then(res => {
                const result = res.data
                this.name = result.name
                this.code = result.code
                this.price = result.price
            }).catch(error => {
                console.log(error)
            })
        }
    }
</script>
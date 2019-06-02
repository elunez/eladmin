<template>
  <elFrame :src="swaggerApi"/>
</template>
<script>
import { mapGetters } from 'vuex'
import elFrame from '@/components/iframe/index'
export default {
  components: { elFrame },
  computed: {
    ...mapGetters([
      'swaggerApi'
    ])
  }
}
</script>

<template>
  <elFrame :src="sqlApi"/>
</template>
<script>
import { mapGetters } from 'vuex'
import elFrame from '@/components/iframe/index'
export default {
  components: { elFrame },
  computed: {
    ...mapGetters([
      'sqlApi'
    ])
  }
}
</script>

import {
  HeaderNav,
  ModalManagerBook
} from '@/components'

import {
  mapActions,
  mapGetters
} from 'vuex'

export default {
  components: {
    HeaderNav,
    ModalManagerBook
  },
  props: ['searchText'],
  data () {
    return {
      modalOptions: {
        isRegister: false,
        isEditing: false,
        bookToEdit: {}
      }
    }
  },
  mounted () {
    this.actionGetSixBooks()
  },
  computed: {
    ...mapGetters([
      'getterSixBooks'
    ])
  },
  methods: {
    ...mapActions([
      'actionGetSixBooks',
      'actionDeleteBook',
      'actionFindBooks'
    ]),
    openEditingModal (book) {
      this.modalOptions.bookToEdit = book
      this.modalOptions.isEditing = true
    },
    confirmDeleteBook (id) {
      this.$confirm('When you delete this book, you will not be able to recover it. Do you want to continue?', 'Attencion', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(() => this.deleteBook(id))

        .catch((cancel) => Promise.resolve(cancel))
    },
    deleteBook (id) {
      this.actionDeleteBook(id)
        .then(() => {
          this.$notify({
            title: 'Success',
            message: 'The Book was successfully deleted!',
            type: 'success'
          })
        })
        .catch(() => {
          this.$notify.error({
            title: 'Ops!',
            message: 'An error occurred during the process...'
          })
        })
    },
    findBooks (searchText) {
      searchText = typeof this.searchText != "undefined" ? this.searchText : "";
      this.actionFindBooks(searchText)
    },

    closeModal () {
      this.modalOptions.isEditing = this.modalOptions.isRegister = false
      this.modalOptions.bookToEdit = {}
    }
  }
}

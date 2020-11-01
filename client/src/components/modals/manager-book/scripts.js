import { mapActions } from 'vuex'

export default {
  props: ['isOpenModal', 'modalOptions'],
  data () {
    return {
      isbn: '',
      title: '',
      listOfAuthors: '',
      genre: '',
      numberOfPages: '',
      inRequest: false
    }
  },
  watch: {
    isOpenModal () {
      this.managerModalValues(this.modalOptions.bookToEdit)
    }
  },
  computed: {
    modalTitle () {
      if (this.modalOptions.isEditing) {
        return 'Edit a book'
      }

      return 'Register a book'
    }
  },
  methods: {
    ...mapActions([
      'actionEditBook',
      'actionAddBook'
    ]),
    saveModalData () {
      this.inRequest = true

      const book = {
        isbn: this.isbn,
        title: this.title,
        listOfAuthors: this.listOfAuthors,
        genre: this.genre,
        numberOfPages: this.numberOfPages
      }

      if (this.modalOptions.isEditing) {
        this.sendRequest(this.actionEditBook, { id: this.modalOptions.bookToEdit.id, book })
      } else {
        this.sendRequest(this.actionAddBook, book)
      }
    },
    sendRequest (targetAction, data) {
      targetAction(data)
        .then(() => {
          this.inRequest = false

          this.$emit('closeManagerBookModal')

          this.$notify({
            title: 'Success',
            message: 'The Book was successfully saved!',
            type: 'success'
          })
        })
        .catch(() => {
          this.inRequest = false

          this.$notify.error({
            title: 'Ops!',
            message: 'An error occurred during the process...'
          })
        })
    },
    managerModalValues (value) {
      this.isbn = value.isbn || ''

      this.title = value.title || ''

      this.listOfAuthors = value.listOfAuthors || ''

      this.genre = value.genre || ''

      this.numberOfPages = value.numberOfPages || ''
    }
  }
}

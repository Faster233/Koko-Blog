<template>
  <el-dialog
    :title="!dataForm.adminUserId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="管理员登陆名称" prop="loginUserName">
      <el-input v-model="dataForm.loginUserName" placeholder="管理员登陆名称"></el-input>
    </el-form-item>
    <el-form-item label="管理员登陆密码" prop="loginPassword">
      <el-input v-model="dataForm.loginPassword" placeholder="管理员登陆密码"></el-input>
    </el-form-item>
    <el-form-item label="管理员显示昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="管理员显示昵称"></el-input>
    </el-form-item>
    <el-form-item label="是否锁定 0未锁定 1已锁定无法登陆" prop="locked">
      <el-input v-model="dataForm.locked" placeholder="是否锁定 0未锁定 1已锁定无法登陆"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          adminUserId: 0,
          loginUserName: '',
          loginPassword: '',
          nickName: '',
          locked: ''
        },
        dataRule: {
          loginUserName: [
            { required: true, message: '管理员登陆名称不能为空', trigger: 'blur' }
          ],
          loginPassword: [
            { required: true, message: '管理员登陆密码不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '管理员显示昵称不能为空', trigger: 'blur' }
          ],
          locked: [
            { required: true, message: '是否锁定 0未锁定 1已锁定无法登陆不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.adminUserId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.adminUserId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/adminuser/info/${this.dataForm.adminUserId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.loginUserName = data.adminUser.loginUserName
                this.dataForm.loginPassword = data.adminUser.loginPassword
                this.dataForm.nickName = data.adminUser.nickName
                this.dataForm.locked = data.adminUser.locked
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/blog/adminuser/${!this.dataForm.adminUserId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'adminUserId': this.dataForm.adminUserId || undefined,
                'loginUserName': this.dataForm.loginUserName,
                'loginPassword': this.dataForm.loginPassword,
                'nickName': this.dataForm.nickName,
                'locked': this.dataForm.locked
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>



## BIO blocking
- serverSocket.accept()等待连接
- InputStream.read(), OutputStream.write() 等待输入

## NIO
使用channel代替Stream

用 Selector 监控多个 Channel

用一个线程里处理多个Channel

## Channel
![](./_images/channel.png)



## BIO to NIO
Socket -> ServerSocketChannel

ThreadPool -> Selector




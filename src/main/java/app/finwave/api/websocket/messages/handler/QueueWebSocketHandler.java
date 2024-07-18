package app.finwave.api.websocket.messages.handler;

import app.finwave.api.websocket.messages.ResponseMessage;
import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

public abstract class QueueWebSocketHandler extends AbstractWebSocketHandler {
    protected ArrayDeque<CompletableFuture<ResponseMessage>> deque = new ArrayDeque<>();
    protected ReentrantLock lock = new ReentrantLock();

    @Override
    public void onMessage(ResponseMessage message) {
        lock.lock();

        try {
            CompletableFuture<ResponseMessage> future = deque.peek();

            if (future == null || future.isDone()) {
                deque.add(CompletableFuture.completedFuture(message));

                return;
            }

            deque.remove(future);
            future.complete(message);
        }finally {
            lock.unlock();
        }
    }

    public CompletableFuture<ResponseMessage> messageExpected() {
        lock.lock();

        try {
            CompletableFuture<ResponseMessage> future = deque.peek();

            if (future != null && future.isDone())
                deque.remove(future);

            if (future != null)
                return future;

            future = new CompletableFuture<>();
            deque.add(future);

            return future;
        }finally {
            lock.unlock();
        }
    }
}

"""
a sample daemonization script for the sqs listener
"""

import sys
from sqs_listener.daemon import Daemon
from sqs_listener import SqsListener


class KundenListener(SqsListener):
    def handle_message(self, body, attributes, messages_attributes):
        pass

        # run your code here
        for x in body:
            print(x)
            for y in body[x]:
                print(y, ':', body[x][y])


class KundenDaemon(Daemon):
    def run(self):
        print "Initializing listener"
        listener = KundenListener('main-queue', a='error-queue')
        listener.listen()


if __name__ == "__main__":
    #pidFile = '/var/run/sqs_daemon.pid'
    pidFile = '/tmp/sqs_daemon.pid'
    daemon = KundenDaemon(pidFile)
    if len(sys.argv) == 2:
        if 'start' == sys.argv[1]:
            print "Starting listener verkundenbot-daemon"
            daemon.start()
        elif 'stop' == sys.argv[1]:
            print "Attempting to stop the verkundenbot-daemon"
            daemon.stop()
        elif 'restart' == sys.argv[1]:
            daemon.restart()
        else:
            print "Unknown command"
            sys.exit(2)
        sys.exit(0)
    else:
        print "usage: %s start|stop|restart" % sys.argv[0]
        sys.exit(2)
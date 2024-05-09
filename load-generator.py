import requests
import threading
import time
import copy

# test 3
# SLEEP = 18
# N_REQUESTS = 3
# URL = "http://149.156.182.229:30111/limited-threads/compute"

# test 2
# SLEEP = 1
# N_REQUESTS = 1
# URL = "http://149.156.182.229:30111/test/get-data-external"

# test 4
s = requests.session()
s.keep_alive = False
s.headers["Connection"] = "close"

SLEEP = 8
N_REQUESTS = 20
N_PER_BATCH = 10
URL = "http://149.156.182.229:32222/actions/schedule-batch"
action = {
  "action" : {
    "name" : "PerformanceAction",
    "args" : 0,
    "state" : "STARTED",
    "retryNum" : 0,
    "idempotencyWindow" : None,
  },
  "idempotencyKey" : None,
  "priority" : 0,
  "delay" : 0
}


def send_request(n):
    try:
        start = time.time()
        res = requests.post(URL,  json = req, headers={'Connection':'close'}).content
        print(n, time.time() - start, res)
    except (KeyboardInterrupt, SystemExit) as e:
        raise e
    except Exception as e:
        print(e)
        pass

for _ in range(20):
        print(requests.get("http://149.156.182.229:32222/test/performance").content)

iter = 1
while True:
    req = [copy.deepcopy(action) for _ in range(N_PER_BATCH)]
    for i, r in enumerate(req):
        r["action"]["args"] = i - N_PER_BATCH
    for i in range(N_REQUESTS):
        for r in req:
            r["action"]["args"] += N_PER_BATCH
        threading.Thread(target=lambda: send_request(i), daemon=True).start()
    iter += 1
    if iter % 100 == 0:
        N_REQUESTS = min(4, N_REQUESTS + 1)
    time.sleep(SLEEP)
    for _ in range(20):
        print(requests.get("http://149.156.182.229:32222/test/performance").content)
    time.sleep(1)

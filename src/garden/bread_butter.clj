^{:nextjournal.clerk/visibility :hide}
(ns garden.bread-butter
  (:require [clojure.string :as str]
            [nextjournal.clerk :as clerk]))

;; # 🍞 Bread and Butter Functions 🧈
;; ## From [clojure-doc.org](https://clojure-doc.org/articles/tutorials/introduction/#bread-and-butter-functions)

;; Given Clojure's extensive use of immutability, persistent data structures, and laziness, one of its strong suits is functional programming. To this author, functional programming means:

;; - treating functions just like any other regular value (for example, passing them as args to other functions)
;; - writing and using functions that return other functions
;; - avoiding mutable state, preferring instead Clojure's functional alternatives (map, filter, reduce, etc.) or else just directly using recursion.

;; Let's try out some of the power tools that Clojure comes with. In the subsections that follow, we've left out the corresponding links to clojuredocs for the given functions, but you'll probably want to read the docs and see the examples there to get the full story for each.

;; # `map`
;; With map you can apply a function to every value in a collection. The result is a new collection. You can often use map instead of manually looping over a collection. Some examples using map:
(clerk/example
 (map inc [10 20 30])
 (map str [10 20 30]))

;; You can define the function to be used on-the-fly:

(clerk/example
 (map (fn [x] (str "=" x "=")) [10 20 30]))

;; And `map` knows how to apply the function you give it
;; to multiple collections in a coordinated way:


(clerk/example
 (map (fn [x y] (str x y)) [:a :b :c] [1 2 3]))

;; When working on more than one collection at a time, `map` is smart enough to stop when the shorter of the *colls* runs out of items:

(clerk/example
 (map (fn [x y] (str x y)) [:a :b :c] [1 2 3 4 5 6 7]))

;; # `filter` and `remove`
;; Use `filter` with a predicate function to pare down a collection to just the values for which `(the-pred the-value)` returns `true`:

(clerk/example
 (filter odd? (range 10)))
;; Use `remove` for the opposite effect (which amounts to removing the items for which `(pred val)` returns `true`):

(clerk/example
 (remove odd? (range 10)))
;; You will often find yourself using these functions instead of writing loops like in imperative languages.

;; # `apply`
;; `apply` is for when you have a function which takes individual args, for example, `max`, but the values you'd like to pass to it are in a collection. `apply` "unpacks" the items in the *coll*:

(clerk/example
 (max 1 5 2 8 3)
 (max [1 5 2 8 3]) ;; ERROR
 (apply max [1 5 2 8 3]))
;; A nice feature of apply is that you can supply extra args which you'd like to be treated as if they were part of the collection:

(clerk/example
 (apply max 4 55 [1 5 2 8 3]))

;; # `for`
;; `for` is for generating collections from scratch (again, without needing to resort to manually looping). `for` is similar to Python's "list comprehensions". Some examples of using for:

(clerk/example
 (for [i (range 10)] i)
 (for [i (range 10)] (* i i))
 (for [i (range 10) :when (odd? i)] [i (str "<" i ">")]))

;; Notice we snuck a `:when (odd? i)` in there. for even supports a `:let` modifier in there to set up your values before getting to the body of the for expression.

;; # `reduce`
;; `reduce` is a gem. You use it to apply a function to the first and second items in a *coll* and get a result. Then you apply it to the result you just got and the 3rd item in the *coll*. Then the result of that and the 4th. And so on. The process looks something like this:

(clerk/example
 (reduce + [1 2 3 4 5]))

;; ```clojure
;; → 1 + 2   [3 4 5]
;; → 3       [3 4 5]
;; → 3 + 3   [4 5]
;; → 6       [4 5]
;; → 6 + 4   [5]
;; → 10      [5]
;; → 10 + 5
;; => 5
;; ```

;; And, of course, you can supply your own function if you like:

;; `(reduce (fn [x y] ,,,) [,,,])`
;; A nice additional feature of `reduce` is that you can supply a value for it to start off with:

(clerk/example
 (reduce + 10 [1 2 3 4 5]))

;; This by itself is pretty handy. But it gets even better. Since you can supply an initial argument, and you can supply your own function, you can use a data structure as that initial argument and have your function "build it up" as you go. For example:


(clerk/example
 (reduce (fn [accum x]
          (assoc accum
                 (keyword x)
                 (str x \- (rand-int 100))))
        {}
        ["hi" "hello" "bye"]))

;; ```clojure
;; → {}
;; → {:hi "hi-29"}
;; → {:hi "hi-29" :hello "hello-42"}
;; ⇒  {:hi "hi-29" :hello "hello-42" :bye "bye-10"}
;; ```

;; Building up some accumulator using reduce and your own custom function is a fairly common pattern (and once again allows us to avoid looping and manipulations of anything mutable).

;; # `partial`, `comp`, & `iterate`
;; With `partial` you can create a function which wraps another one and passes it some standard arguments every time, along with the ones you supply right when you call it. For example:

(clerk/example
  (defn lots-of-args [a b c d] (str/join "-" [a b c d]))
  (lots-of-args 10 20 30 40)
  (def fewer-args (partial lots-of-args 10 20 30))
  (fewer-args 40)
  (fewer-args 99))

;; `comp` is for composing a function from other ones. That is, `(comp foo bar baz)` gives you a function that will first call `baz` on whatever you pass it, then `bar` on the result of that, then `foo` on the result of that, and finally returns the result. Here's a silly example:

(clerk/example
(defn wrap-in-stars  [s] (str "*" s "*"))
(defn wrap-in-equals [s] (str "=" s "="))
(defn wrap-in-ats    [s] (str "@" s "@"))
(def wrap-it (comp wrap-in-ats
                   wrap-in-equals
                   wrap-in-stars))
(wrap-it "hi"))

;; Which is the same as:
(clerk/example
(wrap-in-ats (wrap-in-equals (wrap-in-stars "hi"))))

;; `(iterate foo x)` yields an infinite lazy list consisting of:
;; ```clojure
;; (clerk/code
;;  (x
;;   (foo x)
;;   (foo (foo x))
;;   (foo (foo (foo x)))
;;   ,,,))
;; ```

;; To just take the first, say, 5 values from an infinite list, try this:

(clerk/example
(defn square [x] (* x x))
(take 5 (iterate square 2)))

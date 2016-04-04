package lt.neworld.servicerestarttest

import rx.subjects.PublishSubject

/**
 * @author Andrius Semionovas
 * @since 2016-04-04
 */

object Events {
    val log = PublishSubject.create<String>()
}
